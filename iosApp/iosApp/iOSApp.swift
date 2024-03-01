import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        composeApp.KoinKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
